var request = require('request');

request.get('http://localhost:3000/book', function (error, response, body) {
    console.log(body);
});

request.get('http://localhost:3000/book/1', function (error, response, body) {
    console.log(body);
});

request.post('http://localhost:3000/book', {name: 'ABC', price: 3}, function (error, response, body) {
    console.log(body);
});

request.delete('http://localhost:3000/book/1', function (error, response, body) {
    console.log(body);
});

