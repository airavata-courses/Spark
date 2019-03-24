FROM python:3

WORKDIR /app


COPY ./suggestion/requirements.txt /app
COPY ./suggestion/suggestion/ /app/

RUN pip install -r requirements.txt

ENTRYPOINT ["python", "/app/app.py"]