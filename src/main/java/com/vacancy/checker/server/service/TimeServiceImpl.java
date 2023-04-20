package com.vacancy.checker.server.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TimeServiceImpl implements TimeService{
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
