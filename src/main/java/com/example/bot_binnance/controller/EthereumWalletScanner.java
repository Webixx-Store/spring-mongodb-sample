package com.example.bot_binnance.controller;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EthereumWalletScanner {

    private static final String INFURA_URL = "https://mainnet.infura.io/v3/cdd1642fca664ab9960e1c7a1ad07c0f";  // Thay bằng API Key của bạn
    private static final int THREAD_COUNT = 10; // Số luồng để quét song song
    private static final int WALLETS_PER_THREAD = 1000; // Số ví kiểm tra mỗi luồng

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        Web3j web3 = Web3j.build(new HttpService(INFURA_URL));

        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.execute(() -> scanWallets(web3));
        }

        executor.shutdown();
    }

    private static void scanWallets(Web3j web3) {
        for (int i = 0; i < WALLETS_PER_THREAD; i++) {
            try {
                // 1. Tạo Private Key ngẫu nhiên
                String privateKeyHex = EthereumWalletScanner.generateEthereumPrivateKey();

                // 2. Lấy địa chỉ ví từ Private Key
                Credentials credentials = Credentials.create(privateKeyHex);
                String address = credentials.getAddress();

                // 3. Kiểm tra số dư ví
                EthGetBalance balanceWei = web3.ethGetBalance(address, org.web3j.protocol.core.DefaultBlockParameterName.LATEST).send();
                BigDecimal balanceEth = Convert.fromWei(new BigDecimal(balanceWei.getBalance()), Convert.Unit.ETHER);

                if (balanceEth.compareTo(BigDecimal.ZERO) > 0) {
                    System.out.println("🔑 Tìm thấy ví có tiền!");
                    System.out.println("Private Key: " + privateKeyHex);
                    System.out.println("Address: " + address);
                    System.out.println("Balance: " + balanceEth + " ETH");

                    // Ghi lại ví có tiền để sử dụng sau này
                    saveWallet(privateKeyHex, address, balanceEth);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static String generateEthereumPrivateKey() {
        SecureRandom secureRandom = new SecureRandom();
        BigInteger privateKeyInt;

        // Ethereum private key phải nằm trong khoảng [1, secp256k1 max]
        do {
            privateKeyInt = new BigInteger(256, secureRandom);
        } while (privateKeyInt.compareTo(BigInteger.ONE) < 0 || 
                 privateKeyInt.compareTo(new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141", 16)) >= 0);

        // Chuyển sang dạng hexadecimal (64 ký tự)
        return String.format("%064x", privateKeyInt);
    }

    private static void saveWallet(String privateKey, String address, BigDecimal balance) {
        System.out.println("📌 Lưu ví có tiền: " + address + " - " + balance + " ETH");
        // Bạn có thể ghi dữ liệu vào file hoặc database
    }
}
