package com.bytesvc.svc4jpa.cancel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bytesvc.ServiceException;
import com.bytesvc.service.IAccountService;
import com.bytesvc.svc4jpa.dao.IAccountDao;
import com.bytesvc.svc4jpa.model.Account;

@Service("accountServiceCancel4JPA")
public class AccountServiceCancel4JPA implements IAccountService {

	@Autowired
	private IAccountDao accountDao;

	@Transactional(rollbackFor = ServiceException.class)
	public void increaseAmount(String acctId, double amount) throws ServiceException {
		Account account = this.accountDao.findById(acctId);
		account.setAmount(account.getAmount() - amount); // 真实业务中, 请考虑设置乐观锁/悲观锁, 以便并发操作时导致数据不一致
		this.accountDao.update(account);
		System.out.printf("[jpa] undo increase: acct= %s, amount= %7.2f%n", acctId, amount);
	}

	@Transactional(rollbackFor = ServiceException.class)
	public void decreaseAmount(String acctId, double amount) throws ServiceException {
		Account account = this.accountDao.findById(acctId);
		account.setAmount(account.getAmount() + amount); // 真实业务中, 请考虑设置乐观锁/悲观锁, 以便并发操作时导致数据不一致
		this.accountDao.update(account);
		System.out.printf("[jpa] undo decrease: acct= %s, amount= %7.2f%n", acctId, amount);
	}

}
