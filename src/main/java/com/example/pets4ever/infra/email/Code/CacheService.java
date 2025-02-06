package com.example.pets4ever.infra.email.Code;

import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class CacheService {

    private final ConcurrentHashMap<String, LocalDateTime> cache = new ConcurrentHashMap<>();

    public String storageCodeAndReturnIt() {
        String code = new CodeGenerator().generateCode();
        long expirationMinutes = 5;
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(expirationMinutes);
        cache.put(code, expirationTime);
        return code;
    }

    public boolean isCodeValid(String code) {
        LocalDateTime expirationTime = cache.get(code);
        if (expirationTime == null) {
            return false;
        }
        boolean isValid = LocalDateTime.now().isBefore(expirationTime);
        if (!isValid) {
            cache.remove(code);
        }
        return isValid;
    }

    public void removeCode(String code){
        cache.remove(code);
    }

    @Scheduled(fixedRate = 60000)
    public void removeExpiredCodes() {
        LocalDateTime now = LocalDateTime.now();
        Iterator<Map.Entry<String, LocalDateTime>> iterator = cache.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, LocalDateTime> entry = iterator.next();
            if (now.isAfter(entry.getValue())) {
                iterator.remove();
            }
        }
    }
}
