var request = require('request').defaults({
    baseUrl: 'http://localhost:3000'
});

function getBookList() {
    request({method: 'GET', uri: '/book'}, function (error, response, body) {
        console.log(body);
        return body;
    });
}

function getBook(id) {
    request({method: 'GET', uri: '/book/'+id}, function (error, response, body) {
        console.log(body);
        return body;
    });
}

function addBook(book) {
    request({method: 'POST', uri: '/book', json: book}, function (error, response, body) {
        console.log(body);
    });
}

function deleteBook(id) {
    request({method: 'DELETE', uri: '/book/'+id}, function (error, response, body) {
        console.log(body);
    });
}

var bookList = getBookList();
var book = getBook(1);
addBook({name: 'ABC', price: 3});
deleteBook(1);
bookList = getBookList();
