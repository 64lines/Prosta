const $ = require('jQuery')
const events = require('./events')

$(function() {
    const recorder = require('./recorder')
    recorder.collectClipboardData()
    events.applyInputEvents()
})
