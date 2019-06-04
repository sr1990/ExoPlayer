package main

import (
	"crypto/sha1"
	"encoding/hex"
	"fmt"
	"io"
	"net/http"
	"os"
	"path/filepath"
	"strings"
)

var m map[string]string

//function to calculate hash value of the file
func hash_file_sha1(filePath string) (string, error) {
	var returnSHA1String string
	file, err := os.Open(filePath)
	if err != nil {
		return returnSHA1String, err
	}
	defer file.Close()
	hash := sha1.New()
	if _, err := io.Copy(hash, file); err != nil {
		return returnSHA1String, err
	}
	hashInBytes := hash.Sum(nil)[:20]
	returnSHA1String = hex.EncodeToString(hashInBytes)
	return returnSHA1String, nil
}

//function to read files in current directory and generate a map
func readFilesAndGenerateMap() {
	var files []string
	m = make(map[string]string)

	root := "."
	err := filepath.Walk(root, func(path string, info os.FileInfo, err error) error {
		files = append(files, path)
		return nil
	})
	if err != nil {
		panic(err)
	}
	for _, file := range files {
		//fmt.Println(file)
		hash, err := hash_file_sha1(file)
		if err == nil {
			//fmt.Println(hash)
			//split file path to get absolute file name
			fileSlice := strings.Split(file, "/")
			fileName := fileSlice[len(fileSlice)-1]
			//fmt.Printf("File Name: %s", fileName)
			m[fileName] = hash
		}
	}
}

func main() {

	readFilesAndGenerateMap()

	//print map
	fmt.Println("Map Contents: \n")
	for key, value := range m {
		fmt.Println("Key:", key, "Value:", value)
	}

	http.HandleFunc("/", index)
	http.HandleFunc("/auth", AuthHandleFunc)
	http.ListenAndServe(port(), nil)
}

func port() string {
	port := os.Getenv("PORT")
	if len(port) == 0 {
		port = "9090"
	}
	return ":" + port
}

func index(w http.ResponseWriter, r *http.Request) {
	w.WriteHeader(http.StatusOK)
	fmt.Fprintf(w, "Welcome to DASH Segment Authentication Server - message about queries")
}

// AuthHandleFunction
func AuthHandleFunc(w http.ResponseWriter, r *http.Request) {
	message := r.URL.Query()["base"][0]
	resp := m[message]
	fmt.Println("Resp: ", resp)
	w.Header().Add("Content-Type", "text/plain")
	fmt.Fprintf(w, resp)
}
