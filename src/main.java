import java.security.Security;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

import com.google.gson.GsonBuilder;
import MikeyChain.Wallet; // <--- importing the package i made in the wallet class

public class main {
    public static ArrayList<Block> mBlockchain = new ArrayList<Block>();
    public static HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>(); //list of all unspent transactions.
    public static int mDifficulty = 5;
    public static Wallet walletA;
    public static Wallet walletB;


    public static void main(String[] args)
    {
        //Setup Bouncey castle as a Security Provider
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        //Create the new wallets
        walletA = new Wallet();
        walletB = new Wallet();

        //Test public and private keys
        System.out.println("Private and public keys:");
        System.out.println("Private Key --> " + StringUtil.getStringFromKey(walletA.privateKey));
        System.out.println("Public Key --> " + StringUtil.getStringFromKey(walletA.publicKey));

        //Create a test transaction from WalletA to walletB
        Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
        transaction.generateSignature(walletA.privateKey);

        //Verify the signature works and verify it from the public key
        System.out.println("Is signature verified");
        System.out.println(transaction.verifiySignature());
    }

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[mDifficulty]).replace('\0', '0');

        //loop through blockchain to check hashes:
        for(int i=1; i < mBlockchain.size(); i++) {
            currentBlock = mBlockchain.get(i);
            previousBlock = mBlockchain.get(i-1);
            //compare registered hash and calculated hash:
            if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
                System.out.println("Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            //check if hash is solved
            if(!currentBlock.hash.substring( 0, mDifficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }
}
