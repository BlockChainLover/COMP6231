package ServerImplementaiton;

/**
 * Holder class for : ServerIMpl
 * 
 * @author OpenORB Compiler
 */
final public class ServerIMplHolder
        implements org.omg.CORBA.portable.Streamable
{
    /**
     * Internal ServerIMpl value
     */
    public ServerImplementaiton.ServerIMpl value;

    /**
     * Default constructor
     */
    public ServerIMplHolder()
    { }

    /**
     * Constructor with value initialisation
     * @param initial the initial value
     */
    public ServerIMplHolder(ServerImplementaiton.ServerIMpl initial)
    {
        value = initial;
    }

    /**
     * Read ServerIMpl from a marshalled stream
     * @param istream the input stream
     */
    public void _read(org.omg.CORBA.portable.InputStream istream)
    {
        value = ServerIMplHelper.read(istream);
    }

    /**
     * Write ServerIMpl into a marshalled stream
     * @param ostream the output stream
     */
    public void _write(org.omg.CORBA.portable.OutputStream ostream)
    {
        ServerIMplHelper.write(ostream,value);
    }

    /**
     * Return the ServerIMpl TypeCode
     * @return a TypeCode
     */
    public org.omg.CORBA.TypeCode _type()
    {
        return ServerIMplHelper.type();
    }

}
