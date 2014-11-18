package ServerImplementaiton;

/** 
 * Helper class for : ServerIMpl
 *  
 * @author OpenORB Compiler
 */ 
public class ServerIMplHelper
{
    /**
     * Insert ServerIMpl into an any
     * @param a an any
     * @param t ServerIMpl value
     */
    public static void insert(org.omg.CORBA.Any a, ServerImplementaiton.ServerIMpl t)
    {
        a.insert_Object(t , type());
    }

    /**
     * Extract ServerIMpl from an any
     *
     * @param a an any
     * @return the extracted ServerIMpl value
     */
    public static ServerImplementaiton.ServerIMpl extract( org.omg.CORBA.Any a )
    {
        if ( !a.type().equivalent( type() ) )
        {
            throw new org.omg.CORBA.MARSHAL();
        }
        try
        {
            return ServerImplementaiton.ServerIMplHelper.narrow( a.extract_Object() );
        }
        catch ( final org.omg.CORBA.BAD_PARAM e )
        {
            throw new org.omg.CORBA.MARSHAL(e.getMessage());
        }
    }

    //
    // Internal TypeCode value
    //
    private static org.omg.CORBA.TypeCode _tc = null;

    /**
     * Return the ServerIMpl TypeCode
     * @return a TypeCode
     */
    public static org.omg.CORBA.TypeCode type()
    {
        if (_tc == null) {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
            _tc = orb.create_interface_tc( id(), "ServerIMpl" );
        }
        return _tc;
    }

    /**
     * Return the ServerIMpl IDL ID
     * @return an ID
     */
    public static String id()
    {
        return _id;
    }

    private final static String _id = "IDL:ServerImplementaiton/ServerIMpl:1.0";

    /**
     * Read ServerIMpl from a marshalled stream
     * @param istream the input stream
     * @return the readed ServerIMpl value
     */
    public static ServerImplementaiton.ServerIMpl read(org.omg.CORBA.portable.InputStream istream)
    {
        return(ServerImplementaiton.ServerIMpl)istream.read_Object(ServerImplementaiton._ServerIMplStub.class);
    }

    /**
     * Write ServerIMpl into a marshalled stream
     * @param ostream the output stream
     * @param value ServerIMpl value
     */
    public static void write(org.omg.CORBA.portable.OutputStream ostream, ServerImplementaiton.ServerIMpl value)
    {
        ostream.write_Object((org.omg.CORBA.portable.ObjectImpl)value);
    }

    /**
     * Narrow CORBA::Object to ServerIMpl
     * @param obj the CORBA Object
     * @return ServerIMpl Object
     */
    public static ServerIMpl narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof ServerIMpl)
            return (ServerIMpl)obj;

        if (obj._is_a(id()))
        {
            _ServerIMplStub stub = new _ServerIMplStub();
            stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
            return stub;
        }

        throw new org.omg.CORBA.BAD_PARAM();
    }

    /**
     * Unchecked Narrow CORBA::Object to ServerIMpl
     * @param obj the CORBA Object
     * @return ServerIMpl Object
     */
    public static ServerIMpl unchecked_narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof ServerIMpl)
            return (ServerIMpl)obj;

        _ServerIMplStub stub = new _ServerIMplStub();
        stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
        return stub;

    }

}
