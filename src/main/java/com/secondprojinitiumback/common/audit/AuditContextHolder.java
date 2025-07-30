package com.secondprojinitiumback.common.audit;

public class AuditContextHolder {

    // Client IP와 프로그램 ID를 저장하기 위한 ThreadLocal 변수
    // ThreadLocal은 각 스레드마다 독립적인 값을 저장할 수 있고 멀티스레드 환경에서 안전하게 사용할 수 있도록 클라이언트의 IP 주소와 프로그램 ID를 관리
    private static final ThreadLocal<String> CLIENT_IP = new ThreadLocal<>();
    private static final ThreadLocal<Long> PROGRAM_ID = new ThreadLocal<>();

    public static void setClientIp(String ip) {
        CLIENT_IP.set(ip);
    }

    public static String getClientIp() {
        return CLIENT_IP.get();
    }

    public static void clearClientIp() {
        CLIENT_IP.remove();
    }

    public static void setProgramId(Long programId) {
        PROGRAM_ID.set(programId);
    }

    public static Long getProgramId() {
        return PROGRAM_ID.get();
    }

    public static void clearProgramId() {
        PROGRAM_ID.remove();
    }
}
