var request = require('request').defaults({
    baseUrl: 'http://localhost:3000'
});

function getBookList(cb) {
    request({method: 'GET', uri: '/book'}, function (error, response, body) {
        if(cb)
            cb(body)
    });
}

function getBook(id, cb) {
    request({method: 'GET', uri: '/book/'+id}, function (error, response, body) {
        if(cb)
            cb(body)
    });
}

function addBook(book, cb) {
    request({method: 'POST', uri: '/book', json: book}, function (error, response, body) {
        if(cb)
            cb(body)
    });
}

function deleteBook(id, cb) {
    request({method: 'DELETE', uri: '/book/'+id}, function (error, response, body) {
        if(cb)
            cb(body)
    });
}

getBookList(function (bookList) {
   console.log(bookList);
   //After the getBookList Request is finished

   getBook(1, function (book) {
       console.log(book);
       //After the getBook Request is finished

       addBook({name: 'ABC', price: 3}, function (msg) {
           console.log(msg);
           //After the addBook Request is finished

           deleteBook(1, function (msg) {
               console.log(msg);
               //After the deleteBook Request is finished

               getBookList(function (bookList) {
                   console.log(bookList);

               });
           });
       });
   });
});


