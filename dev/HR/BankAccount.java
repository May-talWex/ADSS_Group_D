package HR;

public class BankAccount {
    int bankNumber;
    int branchNumber;
    int accountNumber;

    public BankAccount(int bankNumber, int accountNumber, int branchNumber) {
        this.bankNumber = bankNumber;
        this.accountNumber = accountNumber;
        this.branchNumber = branchNumber;
    }

    public int getBankNumber() {
        return bankNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public int getBranchNumber() {
        return branchNumber;
    }

    public void setBankNumber(int bankNumber) {
        this.bankNumber = bankNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBranchNumber(int branchNumber) {
        this.branchNumber = branchNumber;
    }

    public void printBankAccount() {
        System.out.println("Bank Number: " + bankNumber);
        System.out.println("Branch Number: " + branchNumber);
        System.out.println("Account Number: " + accountNumber);
    }

    public String toString() {
        return bankNumber + "-" + branchNumber + "-" + accountNumber;
    }

}
