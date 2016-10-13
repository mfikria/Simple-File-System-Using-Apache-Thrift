/**
 * File System Service
 * @version 1.0
 */
namespace java com.pat.filesystem.services  // defines the namespace 

typedef i32 int
typedef i64 int64
typedef string DateTime
typedef list<FileAttribute> fileAttributeList

struct FileChunk {
    1:string uid
    2:binary data
    3:i64 remaining
}

struct FileAttribute {
    1:string fileName
    2:bool directory
    3:int64 size
    4:DateTime lastModifiedDate
    5:DateTime createdDate
}

service FileSystemService { 
    fileAttributeList dir(1:string path),
    bool createDir(1:string path, 2:string dirName),
    FileChunk getBytes(1:string path, 2:string fileName, 3:int64 offset, 4:int size),
    bool putfile(1:string path, 2:string fileName, 3:FileChunk fileChunk, 4:int64 offset, 5:int size)
}