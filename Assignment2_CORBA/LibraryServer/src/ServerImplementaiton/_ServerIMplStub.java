package ServerImplementaiton;

/**
 * Interface definition: ServerIMpl.
 * 
 * @author OpenORB Compiler
 */
public class _ServerIMplStub extends org.omg.CORBA.portable.ObjectImpl
        implements ServerIMpl
{
    static final String[] _ids_list =
    {
        "IDL:ServerImplementaiton/ServerIMpl:1.0"
    };

    public String[] _ids()
    {
     return _ids_list;
    }

    private final static Class _opsClass = ServerImplementaiton.ServerIMplOperations.class;

    /**
     * Operation createAccount
     */
    public String createAccount(String username, String password, String firstname, String lastname, String phonenumber, String email, String educationalinstitute)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("createAccount",true);
                    _output.write_string(username);
                    _output.write_string(password);
                    _output.write_string(firstname);
                    _output.write_string(lastname);
                    _output.write_string(phonenumber);
                    _output.write_string(email);
                    _output.write_string(educationalinstitute);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("createAccount",_opsClass);
                if (_so == null)
                   continue;
                ServerImplementaiton.ServerIMplOperations _self = (ServerImplementaiton.ServerIMplOperations) _so.servant;
                try
                {
                    return _self.createAccount( username,  password,  firstname,  lastname,  phonenumber,  email,  educationalinstitute);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation reserveBook
     */
    public String reserveBook(String username, String password, String bookname, String authorname)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("reserveBook",true);
                    _output.write_string(username);
                    _output.write_string(password);
                    _output.write_string(bookname);
                    _output.write_string(authorname);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("reserveBook",_opsClass);
                if (_so == null)
                   continue;
                ServerImplementaiton.ServerIMplOperations _self = (ServerImplementaiton.ServerIMplOperations) _so.servant;
                try
                {
                    return _self.reserveBook( username,  password,  bookname,  authorname);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation getNonreturners
     */
    public String getNonreturners(String AdminUsername, String AdminPassword, String bookname, String authorName)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("getNonreturners",true);
                    _output.write_string(AdminUsername);
                    _output.write_string(AdminPassword);
                    _output.write_string(bookname);
                    _output.write_string(authorName);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("getNonreturners",_opsClass);
                if (_so == null)
                   continue;
                ServerImplementaiton.ServerIMplOperations _self = (ServerImplementaiton.ServerIMplOperations) _so.servant;
                try
                {
                    return _self.getNonreturners( AdminUsername,  AdminPassword,  bookname,  authorName);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation reserveInterLibrary
     */
    public String reserveInterLibrary(String username, String password, String bookname, String authorname)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("reserveInterLibrary",true);
                    _output.write_string(username);
                    _output.write_string(password);
                    _output.write_string(bookname);
                    _output.write_string(authorname);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("reserveInterLibrary",_opsClass);
                if (_so == null)
                   continue;
                ServerImplementaiton.ServerIMplOperations _self = (ServerImplementaiton.ServerIMplOperations) _so.servant;
                try
                {
                    return _self.reserveInterLibrary( username,  password,  bookname,  authorname);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation exit
     */
    public String exit()
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("exit",true);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("exit",_opsClass);
                if (_so == null)
                   continue;
                ServerImplementaiton.ServerIMplOperations _self = (ServerImplementaiton.ServerIMplOperations) _so.servant;
                try
                {
                    return _self.exit();
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation setDuration
     */
    public String setDuration(String username, String bookname, String limit)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("setDuration",true);
                    _output.write_string(username);
                    _output.write_string(bookname);
                    _output.write_string(limit);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("setDuration",_opsClass);
                if (_so == null)
                   continue;
                ServerImplementaiton.ServerIMplOperations _self = (ServerImplementaiton.ServerIMplOperations) _so.servant;
                try
                {
                    return _self.setDuration( username,  bookname,  limit);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

}
