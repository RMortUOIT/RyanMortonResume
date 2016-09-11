This project is a lightweight app that displays the scores of NHL games with the help of their
free API. This project uses a simple http request from the free NHL API to retrieve live updating
scores, once retrieved as a JSON object the raw scores are parsed and displayed as a scoreboard.

The code example in this project is the source in its entirety since it makes use of a few important
tools, such as the basics of the JFrame library, making a basic HTTP request and parsing the result
using the JSON library.

Note that the source uses the external apache HTTP library and the external JSON library but these
are not inlcuded.

The two JARs included are NHLsetDate and NHLliveDate, the set date example uses an arbitrary date
since the live version has no data to display if there are no games today (which at the time of this
posting the season hasn't started yet). The live date example uses the current date and will actually
update as games are being played.

