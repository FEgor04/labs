config.devServer = config.devServer || {}
        config.devServer.proxy = {
            '/api': {
                target: "http://localhost:8080",
                secure: false,
                bypass: function (req, res, proxyOptions) {
                    if (req.headers.accept.indexOf('.js') !== -1) {
                        return req.headers.accept;
                    }
                }
            }
    }