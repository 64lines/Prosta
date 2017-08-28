// This file is required by the index.html file and will
// be executed in the renderer process for that window.
// All of the Node.js APIs are available in this process.
const $ = require('jQuery')
const clipboardy = require('clipboardy')
const events = require('./events')

const recorder = {
    
    /**
     * Adds new item and applies the events on it.
     */
    addItem: (element, text) => {
        let child = $('<a>').attr('href', '#')
            .addClass('list-group-item').text(text)
        element.append(child)
        events.applyItemEvents()
    },

    /**
     * Gets the clipboard data, adds it to the clipboard list
     * and adds it to the clipboard element.
     */
    collectClipboardData: () => {
        let clipboardList = localStorage.getItem('clipboardList').split(',') || []

        $.each(clipboardList, (key, text) => {
            recorder.addItem($('#clipboard'), text)
        })

        let interval = setInterval(() => {
            const text = clipboardy.readSync();
            const hasText = clipboardList.indexOf(text) == -1
        
            if (hasText) {
                recorder.addItem($('#clipboard'), text)
                clipboardList.push(text)
            }
            localStorage.setItem('clipboardList', clipboardList);
        }, 1000)
    }
}
module.exports = recorder


