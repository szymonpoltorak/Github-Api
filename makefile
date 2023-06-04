all: run

run:
	docker build -t github-api .
	docker run -d -p 8080:8080 --name Github-Api github-api
