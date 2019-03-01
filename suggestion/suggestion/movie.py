class Movie:
    def __init__(self, movie_id, poster_path, original_language, release_date, title, vote_count, vote_average):
        self.movie_id = movie_id
        self.poster_path = poster_path
        self.original_language = original_language
        self.release_date = release_date
        self.title = title
        self.vote_count=vote_count
        self.vote_average = vote_average

    def returndict(self):
        return({'movie_id': self.movie_id,
                'poster_path': self.poster_path,
                'original_language': self.original_language,
                'release_date': self.release_date,
                'title': self.title,
                'vote_count': self.vote_count,
                'vote_average': self.vote_average})
