var request = require('request');

function getBookList(callback) {
    request.get('http://localhost:3000/book', function (error, response, body) {
        callback(body)
    });
}

function getBook(id, callback) {
    request.get('http://localhost:3000/book/' + id, function (error, response, body) {
        callback(body)
    });
}

function addBook(book, callback) {
    request.post('http://localhost:3000/book', book, function (error, response, body) {
        callback(body)
    });
}

function deleteBook(id, callback) {
    request.delete('http://localhost:3000/book/1', function (error, response, body) {
        callback(body)
    });
}

getBookList(function (bookList) {
   console.log(bookList);
   //after end of getBookList Request

   getBook(1, function (book) {
       console.log(book);
       //after end of getBook Request

       addBook({name: 'ABC', price: 3}, function (msg) {
           console.log(msg);
           //after end of addBook Request

           deleteBook(2, function (msg) {
               console.log(msg);
               //after end of deleteBook Request

               getBookList(function (bookList) {
                   console.log(bookList);
               });
           });
       });
   });
});


