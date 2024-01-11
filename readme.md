# Simple Java-based web crawler #
## Install ##
Unpack the .zip file under the /release/0.0.2 folder in local folder of your choice.

Pre-requisite:
- Java 8 or newer installed on your system.

## Usage ##

Use the command bin/crawl:


	Usage: crawl -u <url> -x [xpath] -d [crawling depth] -o [output] [--flatten]
	crawl -u https://jsoup.org -x //div[@class='col1']/p,//div[@class='col2']/p -d 2 -o /users/name/output --flatten
	Arguments:
	-u  Starting point URL to crawl. Mandatory
	-x  The xpath expressions to look for in this page and all sub pages. Comma separated. Optional
	-d  The crawling depth of links to look for, default is 1, maximum is 3. Optional
	-o  Output path to store text files, will create a directory structure from path. Optional
	-h  Comma separated list of hosts to limit this crawl to, e.g. -h jsoup.org,en.wikipedia.org
	--flatten   Will not create a directory structure, but store all files in same directory with a normalized file name
	--silent    Does not print anything except the output (if output path is not defined). Optional
	--skipln    Skip line breaks between elements in content parsed. Optional

