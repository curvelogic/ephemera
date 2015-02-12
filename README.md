# ephemera

A tiny Clojure library intended to ease any testing that requires
having some dummy files on disk.

It is not always feasible to mock out file system access in unit
tests. 

## Usage

*ephemera.fs* provides a `write-tree!` function which makes it
very easy to write create a temporary directory complete with file
content.

Simply pass a nested map of filenames and content and
`write-tree!` generates the whole lot returning a `File`
instance for the root.


````clojure
(use 'ephemera.fs)
;; => nil
(def root (write-tree!
             {"file-a" "This content goes in file-a"
              "subdir" {"file-b.txt" "More text\nin here\n"
                         "foo.sh" "#!/bin/bash\necho Hello World!"}}))
;; => #'user/root
root
;; => #<File /var/folders/qx/j3bsbrc56xz_63gpcg2sldzm0000gn/T/4409939888475616075>
````

````bash
$ ls -R /var/folders/qx/j3bsbrc56xz_63gpcg2sldzm0000gn/T/4409939888475616075
file-a	subdir

/var/folders/qx/j3bsbrc56xz_63gpcg2sldzm0000gn/T/4409939888475616075/subdir:
file-b.txt	foo.sh
$ cat /var/folders/qx/j3bsbrc56xz_63gpcg2sldzm0000gn/T/4409939888475616075/subdir/foo.sh
#!/bin/bash
echo Hello World!
````

Currently the file content must be a string or a nested map. If you
need anything else, simply extend the `ephemera.fs/FSContent` protocol
to the type you need.

The map is just a map.

That's it.

## License

Copyright © 2015 Greg Hawkins

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
