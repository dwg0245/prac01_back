package com.example.demo.relation.service;

import com.example.demo.relation.model.A;
import com.example.demo.relation.repository.ARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AService {
    private final ARepository aRepository;


    public void read(Long idx) {
        A a = aRepository.findById(idx).orElseThrow();

        System.out.println(a.getIdx());
        System.out.println(a.getA01());

        // B라는 걸로 뭘 할때 B를 가져오는 SQL문이 실행이된다.
        System.out.println(a.getBList().get(0).getIdx());
        System.out.println(a.getBList().get(0).getB01());
        System.out.println(a.getBList().get(0).getB02());
        System.out.println(a.getBList().get(0).getA());
        System.out.println(" ");
    }

    public void readList() {
        List<A> aData = aRepository.findAll();

        for(A a : aData){
            System.out.println(a.getIdx());
            System.out.println(a.getA01());

            System.out.println(a.getBList().get(0).getIdx());
            System.out.println(a.getBList().get(0).getB01());
            System.out.println(a.getBList().get(0).getB02());
            System.out.println(a.getBList().get(0).getA());
        }

        System.out.println(" ");
    }
}
