import com.google.gson.*;

import java.util.ArrayList;

public class main {
    public static ArrayList<Block> blockchain = new ArrayList<Block>();

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


        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockchainJson);
    }
}
