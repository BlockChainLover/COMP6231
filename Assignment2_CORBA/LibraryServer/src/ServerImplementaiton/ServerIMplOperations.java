package ServerImplementaiton;

/**
 * Interface definition: ServerIMpl.
 * 
 * @author OpenORB Compiler
 */
public interface ServerIMplOperations
{
    /**
     * Operation createAccount
     */
    public String createAccount(String username, String password, String firstname, String lastname, String phonenumber, String email, String educationalinstitute);

    /**
     * Operation reserveBook
     */
    public String reserveBook(String username, String password, String bookname, String authorname);

    /**
     * Operation getNonreturners
     */
    public String getNonreturners(String AdminUsername, String AdminPassword, String bookname, String authorName);

    /**
     * Operation reserveInterLibrary
     */
    public String reserveInterLibrary(String username, String password, String bookname, String authorname);

    /**
     * Operation exit
     */
    public String exit();

    /**
     * Operation setDuration
     */
    public String setDuration(String username, String bookname, String limit);

}
