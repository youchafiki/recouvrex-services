package com.recouvrex.process.utils;

import java.util.UUID;

public class IdentificationTool {

    public static String generateCaseId(){return UUID.randomUUID().toString();}
}
