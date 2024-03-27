package com.recouvrex.process.service;

import com.recouvrex.process.model.Status;

import java.util.List;

public interface StatusService {
     Status save(Status status);

     List<Status> listStatus();
}
