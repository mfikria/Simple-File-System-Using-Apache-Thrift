# Simple-File-System-Using-Apache-Thrift
Aplikasi distributed file system sederhana berbasis RPC dengan menggunakan Apache Thrift

---

### Client
Program client berupa CLI (command line interpreter) yang menunggu command dari user,
antara lain:

* DIR [path]: menampilkan daftar file dan direktori pada path jika diberikan. Jika kosong,
menampilkan isi /. Path menggunakan aturan yang serupa dengan sistem linux (berbasis
root di / )
* CREATEDIR [path] [nama_dir]: membuat direktori baru pada path. Jika direktori path
belum ada, mengembalikan pesan error.
* GETFILE [path] [nama_file] [path lokal]: digunakan untuk mengambil file dari server, dan
menyimpannya di direktori path lokal. Jika ada file lokal dengan nama yang sama,
ditimpa. Jika tidak ada file yang diminta pada server, mengembalikan pesan error.
* PUTFILE [path] [nama_file] [path_lokal]: digunakan untuk menambah file di server pada
lokasi path, dengan nama nama_file yang ada di direktori path_lokal. Jika file sudah ada
pada server, ditimpa.


### Server
Program server yang menyediakan fungsionalitas/layanan yang dapat digunakan oleh program
client di atas. Aplikasi server harus dapat menangani client lebih dari 1 pada saat bersamaan.
Ukuran file yang dikirim/diterima diasumsikan tidak pernah lebih dari 512 byte.
