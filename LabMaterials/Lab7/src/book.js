var express = require('express');
var router = express.Router();

var bookList = [];
var id = 0;

addBook({name: 'UNP', price: 3});
addBook({name: 'Linux Programming Guide', price: 1});
addBook({name: 'Introduction to Optimization', price: 4});

function addBook(book) {
    book.id = id++;
    bookList.push(book)
}

function findBook(id) {
    for(var idx in bookList) {
        if(bookList[idx].id === parseInt(id))
            return idx
    }
}

router.get('/', function(req, res, next) {
    res.json(bookList)
});

router.get('/:id', function(req, res, next) {
    if((idx = findBook(req.params.id)))
        res.json(bookList[idx]);
    else
        res.json({error: 'no book exist'});
});

router.post('/', function(req, res, next) {
    addBook(req.body);
    res.json({msg: 'book enrolled'});
});

router.delete('/:id', function(req, res, next) {
    if((idx = findBook(req.params.id))) {
        bookList.splice(idx, 1);
        res.json({msg: 'book deleted'});
    }
    else
        res.json({error: 'no book exist'});
});

module.exports = router;

