
from sqlalchemy import Column,Integer,Float,Sequence, String
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import create_engine
import json

Base = declarative_base()

class UserMovieRating(Base):
    __tablename__ = 'user_movie_rating'
    user_id = Column(Integer, primary_key=True)
    movie_id   = Column(Integer, primary_key=True)
    movie_name = Column(String(100))
    rating  = Column(Float)

    @property
    def serialize(self):
      """Return object data in easily serializeable format"""
      return {
          'user_id': self.user_id,
          'movie_id': self.movie_id,
          'movie_name': self.movie_name,
          'rating': self.rating,
      }

class MovieGenre(Base):
    __tablename__ = 'movie_genre'
    movie_id = Column(Integer, primary_key=True)
    movie_name = Column(String(100))
    genre  = Column(String(50), primary_key=True)

    @property
    def serialize(self):
      """Return object data in easily serializeable format"""
      return {
          'movie_id': self.movie_id,
          'movie_name': self.movie_name,
          'genre': self.genre,
      }


if __name__ == '__main__':
    engine = create_engine('mysql+pymysql://' + 'root' + ':' + 'aimhigher01' + '@' + 'localhost' +':' + '3306' + '/' + 'movie')
    Base.metadata.drop_all(engine)
    Base.metadata.create_all(engine)
