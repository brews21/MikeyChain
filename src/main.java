import com.google.gson.*;

import java.util.ArrayList;

public class main {
    public static ArrayList<Block> blockchain = new ArrayList<Block>();

    public static int mDifficulty = 5;

    public static void main(String[] args)
    {
        System.out.println("BlockChain Project");

        int mNumberOfBlocks = 10;

        // ading the first node in the chain
        blockchain.add(new Block("Hi im the first block","0"));

        // adding the rest of the nodes
        for (int i = 1; i < mNumberOfBlocks; i++)
        {
            //add our blocks to the blockchain ArrayList:
            String data = "Hi in the " + Integer.toString(i) + " block";
            blockchain.add(new Block(data, blockchain.get(i-1).hash));
        }

        // mining the blockcahin
        for (int i = 0; i < mNumberOfBlocks; i++)
        {
            System.out.println("Mining block -- " + Integer.toString(i));
            blockchain.get(i).mineBlock(mDifficulty);
        }

        System.out.println("\nBlockchain is Valid: " + isChainValid());


        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockchainJson);
    }

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[mDifficulty]).replace('\0', '0');

        //loop through blockchain to check hashes:
        for(int i=1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
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
