package main

import (
	"fmt"
	"github.com/gorilla/handlers"
	"net/http"
	"os"
)

func main() {
	fs := http.FileServer(http.Dir("Insert directory"))

	http.Handle("/", fs)

	fmt.Println("Listening...")
	http.ListenAndServe(":3000", handlers.LoggingHandler(os.Stdout, http.DefaultServeMux))
}
