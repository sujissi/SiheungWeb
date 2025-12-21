package com.signal.web.service;

import com.signal.web.domain.Complaint;
import com.signal.web.dto.complaint.ComplaintRequest;
import com.signal.web.repository.ComplaintRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.signal.web.domain.Member;
import com.signal.web.repository.MemberRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;

@Service
public class ComplaintService {

    private final ComplaintRepository repository;
    private final String uploadPath = "C:/signal-uploads/";
    private final MemberRepository memberRepository; // [추가]

    public ComplaintService(ComplaintRepository repository, MemberRepository memberRepository) {
        this.repository = repository;
        this.memberRepository = memberRepository;
    }

    public Complaint create(String title, String content, String category, String location, MultipartFile file, String username) {
        Complaint c = new Complaint();
        c.setTitle(title);
        c.setContent(content);
        c.setCategory(category);
        c.setLocation(location);
        Member author = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        c.setAuthor(author);

        // 이미지 처리 로직
        if (file != null && !file.isEmpty()) {
            try {
                // 1. 파일 이름 중복 방지를 위해 UUID 생성
                String uuid = UUID.randomUUID().toString();
                String fileName = uuid + "_" + file.getOriginalFilename();

                // 2. 저장할 파일 객체 생성
                File saveFile = new File(uploadPath, fileName);

                // 폴더가 없으면 에러가 날 수 있으므로 폴더 생성 코드 추가
                if (!saveFile.getParentFile().exists()) {
                    saveFile.getParentFile().mkdirs();
                }

                // 3. 실제 파일 저장
                file.transferTo(saveFile);

                // 4. DB에는 "파일 이름"만 저장
                c.setImagePath(fileName);

            } catch (IOException e) {
                e.printStackTrace(); // 에러 로그 출력
            }
        }

        return repository.save(c);
    }

    public Complaint create(ComplaintRequest request) {
        Complaint c = new Complaint();
        c.setTitle(request.getTitle());
        c.setContent(request.getContent());
        c.setCategory(request.getCategory());
        c.setLocation(request.getLocation());
        return repository.save(c);
    }

    public List<Complaint> findAll(){
        return repository.findAll();
    }

    public Complaint findById(Long id){
        return repository.findById(id).orElseThrow(()-> new IllegalArgumentException("못 찾음: " + id));
    }

    @Transactional
    public Complaint update(Long id, ComplaintRequest request){
        Complaint complaint = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("못 찾음: "+ id));

        complaint.setTitle(request.getTitle());
        complaint.setContent(request.getContent());
        complaint.setCategory(request.getCategory());
        complaint.setLocation(request.getLocation());

        return complaint;
    }

    @Transactional
    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new IllegalArgumentException("못 찾음: "+ id);
        }
        repository.deleteById(id);
    }

    public List<Complaint> getTop5() {
        return repository.findTop5ByOrderByLikesDesc();
    }

    public List<Complaint> getByCategory(String category) {
        return repository.findByCategoryOrderByIdDesc(category);
    }

    public List<Complaint> getByStatus(String status) {
        return repository.findByStatusOrderByIdDesc(status);
    }

    public List<Complaint> search(String keyword) {
        return repository.findByTitleContaining(keyword);
    }

    public List<Complaint> getAllPopular() {
        return repository.findAllByOrderByLikesDesc();
    }

    public List<Complaint> getByCategoryAndStatus(String category, String status) {
        return repository.findByCategoryAndStatus(category, status);
    }

    public List<Complaint> getUrgentList() {
        return repository.findUrgentComplaints();
    }

    @Transactional
    public void increaseLikes(Long id) {
        Complaint complaint = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        complaint.setLikes(complaint.getLikes() + 1);
    }

    @Transactional
    public void updateStatus(Long id, String newStatus) {
        Complaint complaint = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("민원글이 없습니다."));
        complaint.setStatus(newStatus);
    }

    @Transactional
    public void registerAnswer(Long id, String answerContent) {
        Complaint complaint = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("민원글이 없습니다."));

        complaint.setAnswer(answerContent); // 답변 내용 저장

        if ("접수".equals(complaint.getStatus())) {
            complaint.setStatus("완료");
        }
    }
    public List<Complaint> findAll(String sort) {
        if ("oldest".equals(sort)) {
            return repository.findAll(Sort.by(Sort.Direction.ASC, "id")); // 과거순 (ID 오름차순)
        } else if ("likes".equals(sort)) {
            return repository.findAll(Sort.by(Sort.Direction.DESC, "likes")); // 공감 많은 순
        } else if ("status".equals(sort)) {
            return repository.findAll(Sort.by(Sort.Direction.ASC, "status")); // 상태별 정렬 (가나다순)
        }

        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
    public List<Complaint> getLatest5() {
        return repository.findTop5ByOrderByCreatedAtDesc();
    }
}