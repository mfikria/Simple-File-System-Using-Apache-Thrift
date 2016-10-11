/**
 * File System Service
 * @version 1.0
 */
namespace java com.pat.filesystem.services  // defines the namespace 

typedef i32 int  //typedefs to get convenient names for your types
typedef list<string> string_list
typedef list<byte> file

service FileSystemService { 
        string_list dir(1:string server_path),
        bool createdir(1:string server_path, 2:string dir_name),
        file getfile(1:string server_path, 2:string file_name),
        bool putfile(1:string server_path, 2:string file_name, 3:file data)
}