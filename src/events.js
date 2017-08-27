const $ = require('jQuery')
const clipboardy = require('clipboardy')


module.exports = {
    /**
     * Applies all the events related to the Inputs
     */
    applyInputEvents: () => {
        $('#search-text').keyup((event) => {
            const searchText = $(event.target).val();
            let clipboardList = localStorage.getItem('clipboardList').split(',')
            $('#clipboard').empty()

            $.each(clipboardList, (key, text) => {
                const recorder = require('./recorder')

                if (text.indexOf(searchText) != -1) 
                {
                    recorder.addItem($('#clipboard'), text)
                }
            })
        });
    },

    /**
     * Applies all the events related to the Item Elements
     */
    applyItemEvents: () => {
        $('.list-group-item').click((event) => {
            $('.list-group-item').removeClass('active')
            $(event.target).addClass('active')
        })

        $('#copy').click((event) => {
            const text = $('.active').text()
            clipboardy.writeSync(text)
        })
    }
}