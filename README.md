# http-puglj

[![Build Status](https://travis-ci.org/TronPaul/http-puglj.svg?branch=master)](https://travis-ci.org/TronPaul/http-puglj)

http server for TF2 pugs written in clojure

## Development

### Requirements

* java
* lein

### Running a dev server

    lein ring server

This will start a development server on port 3000. It will also autoreload on code changes.

## Deployment

`http-puglj` uses Docker for deployments. Use:

    docker build -t <name>/http-puglj .

to build a docker image.

## License

TODO
