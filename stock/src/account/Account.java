package account;
// VO
public class Account {
	private String accountNumber;
	private int bankId;
	private int cash;
	private String bankName;
	
	public Account(String accountNumber, int bankId, int cash, String bankName) {
		super();
		this.accountNumber = accountNumber;
		this.bankId = bankId;
		this.cash = cash;
		this.bankName = bankName;
	}
	public Account(String accountNumber, int cash, String bankName) {
		super();
		this.accountNumber = accountNumber;
		setBankId(bankName);
		this.cash = cash;
		this.bankName = bankName;
	}
	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", bankId=" + bankId + ", cash=" + cash + ", bankName="
				+ bankName + "]";
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public int getBankId() {
		return bankId;
	}
	public void setBankId(String bankName) {
			byte[]	c=bankName.getBytes();
			int id=0;
			int temp=1;
			for(int i=0;i<c.length;i++) {
				id=id+c[i]*temp;
				temp=temp*8;
			}
			this.bankId=id;
	}
	public int getCash() {
		return cash;
	}
	public void setCash(int cash) {
		this.cash = cash;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
}
