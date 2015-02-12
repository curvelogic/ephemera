(ns ephemera.fs-test
  (:require [midje.sweet :refer :all]
            [ephemera.fs :as fs]
            [clojure.java.io :as io]))

(fact "`create-temp-root!` creates directories"
  (fs/create-temp-root!) => #(.isDirectory %))

(fact "`ensure-directory!` accepts existing directory"
  (fs/ensure-directory! (fs/create-temp-root!)) => anything)

(fact "`write-tree!` constructs directory with contents"
  (let [root (fs/write-tree!
              {"file-a.txt" "some contents\nand some more\n"
               "src" {"test.clj" "(ns test)\n"
                      :run "#!/bin/bash\n\necho Hello World\n"}})]
    root => #(.isDirectory %)
    (io/file root "file-a.txt") => #(.exists %)
    (io/file root "src") => #(.isDirectory %)
    (io/file root "src/test.clj") => #(.exists %)
    (io/file root "src/run") => #(.exists %)
    (slurp (io/file root "src/run")) => "#!/bin/bash\n\necho Hello World\n"))
