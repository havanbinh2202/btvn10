package com.tn.Controller;

import com.tn.Entity.Account;
import com.tn.Repository.AccountRepository;
import com.tn.Req.AccountRequpdate;
import com.tn.Req.Accountreq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepo;

    @GetMapping("account")
    public ResponseEntity<?> account(){
            log.info("Get all Account");
        List<Account> accounts = accountRepo.findAll();

        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping("save")
    public  ResponseEntity<?> save(@RequestBody Accountreq accountreq) {
        log.info("Save new account ");

        Account accountDb = accountRepo.findByUsername(accountreq.getUsername());
        if(accountDb != null){
            return new ResponseEntity<>("Account name exitting" + accountreq.getUsername(), HttpStatus.OK);
        }

        Account account = new Account();

        account.setUsername(accountreq.getUsername());
        account.setPassword(accountreq.getPassword());

        accountRepo.save(account);
        log.info("Đã thêm 1 account mới! ",account.getUsername());

        return new ResponseEntity<>("Create sucesslly " + account, HttpStatus.OK);
    }
    @PutMapping("account/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @RequestBody AccountRequpdate accountRequpdate) {

        Account account = accountRepo.findById(id).orElse(null);
        System.out.println(account);
        if (account != null){
            log.warn("Not found with id " + id);
            account.setUsername(accountRequpdate.getUsername());
            account.setPassword(accountRequpdate.getPassword());
            accountRepo.save(account);
            return new ResponseEntity<>("Update suscesslly:", HttpStatus.OK);
        }
        log.info("Đã cập nhật thông tin của account: ", account.getUsername());

        return new ResponseEntity<>("Fail not school with id "+ id, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("account/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){

        Account account = accountRepo.findById(id).orElse(null);
        if (account == null){
            return new ResponseEntity<>("Account id  exiting",HttpStatus.BAD_REQUEST);
        }
        accountRepo.deleteById(id);

        log.info("Đã xóa account có ID: ", id);

        return new ResponseEntity<>("Delete successfully!", HttpStatus.OK);
    }

}
