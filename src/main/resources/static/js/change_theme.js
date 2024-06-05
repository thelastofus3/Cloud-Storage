const themeSwitcher = document.querySelector('#theme-switcher')
const links = themeSwitcher.querySelectorAll('a')

const currentTheme = localStorage.getItem('theme') ?
    localStorage.getItem('theme') :
    document.documentElement.getAttribute('data-bs-theme')

const setTheme = theme => {
    if (theme === 'auto' || theme === null || theme === '') {
        document.documentElement.setAttribute('data-bs-theme', 'dark')
        localStorage.setItem('theme', 'auto')
        updateTextColor('dark')
    } else {
        document.documentElement.setAttribute('data-bs-theme', theme)
        localStorage.setItem('theme', theme)
        updateTextColor(theme)
    }
}

const updateTextColor = theme => {
    const textElements = document.querySelectorAll('.text-white, .text-black');
    textElements.forEach(element => {
        if (theme === 'dark') {
            element.classList.remove('text-black');
            element.classList.add('text-white');
        } else {
            element.classList.remove('text-white');
            element.classList.add('text-black');
        }
    });
};

setTheme(currentTheme)

links.forEach(link => {
    if (localStorage.getItem('theme') === link.getAttribute('data-theme')) {
        link.classList.add('active')
    } else {
        link.classList.remove('active')
    }

    link.addEventListener('click', e => {
        e.preventDefault()

        links.forEach(l => {
            l.classList.remove('active')
        })

        const theme = link.getAttribute('data-theme')
        link.classList.add('active')
        setTheme(theme)
    })
})