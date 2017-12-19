import java.util.Set;
import java.util.HashSet;

class Block{

  public static final long THRESHOLD = 1000000000; // maximum acceptible hash
  public static final int MAX_SIZE = 10;          // maximum number of  transactions

  // public static final Block GENESISBLOCK;
  //
  // /**
  //  * Initializes the genesis block
  //  */
  //  static {
  //    GENESISBLOCK = new GenesisBlock();
  //  }

  private int nonce;
  private Set<String> transactions;
  private int height;
  private boolean closed;
  private Block parent;

  /**
   * Creates a new Block with the specefied block as its parent.
   */
  public Block(Block parent){
    this.parent = parent;
    this.closed = false;
    this.transactions = new HashSet<>();
    if (parent != null)
      this.height = parent.height + 1;
    else
      this.height = 0;
    this.nonce = 0;
  }

  /**
   * After ensuring all transactions are valid, closes this block, and
   * computes a valid proof of work.
   *
   * In order to be valid, a block must have these properties:
   * 1. The block has a valid parent in the chain
   * 2. All transactions are individually valid
   * 3. Every subset of transactions is mutually compatible
   * 4. The proof of work is satisfied
   *
   * @return Whether the mining was successful
   */
  public boolean mine(){
    //TODO implement this

    //Verify all strings in set are not null
    for(String s: transactions){
      if(s == null){
        return false;
      }
    }

    //Has Parent
    if (!closed){
      int hashCode = this.hashCode();
      if (hashCode <= THRESHOLD)
        closed = true;
      else
        nonce++;
    }
    return closed;
  }

  /**
   * Takes in a new transactions
   * @return Whether the transaction was added successfully
   */
  public boolean addTransaction(String t){
      return transactions.size() < MAX_SIZE && transactions.add(t);
  }

  /**
   * Indicates whether the block is mined, or still open for new transactions
   */
  public boolean isMined(){
    //TODO implement this
    return closed;
  }

  /**
   * Calculates the blocks hash. The hash must depend on:
   * 1. Each of the block's transactions
   * 2. The block's parent's hash
   * 3. The block's nonce
   */
  @Override
  public int hashCode(){
    //TODO implement this
    String combined = "";
    for(String tran : transactions){
      combined += ""+tran;
    }
    combined += parent.hashCode();
    combined += "" + (nonce);
    return combined.hashCode();
  }

  public int getHeight(){
    return height;
  }

}
// 1632b0cd-7b27-48b5-93de-27ba35e76090

class GenesisBlock extends Block{

  public GenesisBlock(){
    super(null);
  }
  @Override
  public boolean isMined(){
    return true;
  }

  @Override
  public int hashCode(){
    return 0;
  }
}

public class BlockChainRunner {
  public static void main(String[] args){
    Block lastBlock = new GenesisBlock();
    Block curBlock = new Block(lastBlock);
    while(curBlock.getHeight() < 1000){
      // Add random transactions
      while(curBlock.addTransaction(genTransaction()));
      // Mine the block
      while(!curBlock.mine());
      lastBlock = curBlock;
      curBlock = new Block(lastBlock);
      System.out.println(curBlock.getHeight());

    }
    System.out.println("The chain's height is: " + curBlock.getHeight());
  }

  /**
   * Generates a random string.
   * @return the string.
   */
  public static String genTransaction(){
    return "" + (Math.random());
  }
}
