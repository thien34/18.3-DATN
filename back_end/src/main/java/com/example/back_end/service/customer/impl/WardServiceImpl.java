package com.example.back_end.service.customer.impl;

import com.example.back_end.core.admin.customer.payload.response.WardResponse;
import com.example.back_end.service.customer.WardService;
import com.example.back_end.repository.WardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WardServiceImpl implements WardService {

    private final WardRepository wardRepository;

    @Override
    public List<WardResponse> getAllWard() {
        return wardRepository.findAll().stream()
                .map(ward -> WardResponse.builder()
                        .districtCode(ward.getDistrictCode().getCode())
                        .code(ward.getCode())
                        .nameEn(ward.getNameEn())
                        .build()
                )
                .toList();
    }

}