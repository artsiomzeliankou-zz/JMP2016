package org.shop;

/**
 * The main Data Initializer util class.
 */
public class DataInitializer {

    /** The seller initializer. */
    private SellerInitializer sellerInitializer;
    
    /** The product initializer. */
    private ProductInitializer productInitializer;
    
    /** The proposal initializer. */
    private ProposalInitializer proposalInitializer;
    
    /** The user initializer. */
    private UserInitializer userInitializer;

    /**
     * Inits the data.
     */
    public void initData() {
        sellerInitializer.initSellers();
        userInitializer.initUsers();
        productInitializer.initProducts();
        proposalInitializer.initProposals();
    }
}
