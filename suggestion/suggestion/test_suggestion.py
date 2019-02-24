import unittest
import json
import app
from unittest.mock import Mock, patch
# from nose.tools import assert_is_not_none

class SuggestionTest(unittest.TestCase):
    # @classmethod
    # def setUpClass(cls):
    #     # app.config['SERVER_NAME'] = 'localhost:5000'
    #     # cls.client = app.test_client()
    #     pass
    # @classmethod
    # def tearDownClass(cls):
    #     pass
    #
    # def setUp(self):
    #     app.app.config['Testing'] = True
    #     self.app = app.app.test_client()
        # self.app_context = app.app_context()
        # self.app_context.push()
        # pass
        # # self.app = app.test_client()
        # # self.app.testing = True

    # def tearDown(self):
    #     # self.app_context.pop()
    #     pass

    @patch('app.requests.get')
    def test_request_response_with_decorator(self, mock_get):
        # with patch('app.requests.get') as mock_get:

            mock_get.return_value.status_code = json.dumps([{"movie_id":399579,"original_language":"en","poster_path":"/xRWht48C2V8XNfzvPehyClOvDni.jpg","release_date":"2019-01-31","title":"Alita: Battle Angel","vote_average":6.8,"vote_count":714},{"movie_id":324857,"original_language":"en","poster_path":"/iiZZdoQBEYBv6id8su7ImL0oCbD.jpg","release_date":"2018-12-07","title":"Spider-Man: Into the Spider-Verse","vote_average":8.5,"vote_count":1744},{"movie_id":480530,"original_language":"en","poster_path":"/v3QyboWRoA4O9RbcsqH8tJMe8EB.jpg","release_date":"2018-11-21","title":"Creed II","vote_average":6.6,"vote_count":1348},{"movie_id":490132,"original_language":"en","poster_path":"/7BsvSuDQuoqhWmU2fL7W2GOcZHU.jpg","release_date":"2018-11-16","title":"Green Book","vote_average":8.4,"vote_count":1337},{"movie_id":450465,"original_language":"en","poster_path":"/svIDTNUoajS8dLEo7EosxvyAsgJ.jpg","release_date":"2019-01-16","title":"Glass","vote_average":6.7,"vote_count":1467},{"movie_id":166428,"original_language":"en","poster_path":"/xvx4Yhf0DVH8G4LzNISpMfFBDy2.jpg","release_date":"2019-01-03","title":"How to Train Your Dragon: The Hidden World","vote_average":8,"vote_count":658},{"movie_id":452832,"original_language":"en","poster_path":"/hgWAcic93phg4DOuQ8NrsgQWiqu.jpg","release_date":"2019-01-24","title":"Serenity","vote_average":5.1,"vote_count":62},{"movie_id":505954,"original_language":"ru","poster_path":"/wNJF8R5QE6nBT7DQoKk8t6YD1MM.jpg","release_date":"2018-12-27","title":"T-34","vote_average":4.6,"vote_count":97},{"movie_id":424694,"original_language":"en","poster_path":"/lHu1wtNaczFPGFDTrjCSzeLPTKN.jpg","release_date":"2018-10-24","title":"Bohemian Rhapsody","vote_average":8.1,"vote_count":5331},{"movie_id":404368,"original_language":"en","poster_path":"/lvfIaThG5HA8THf76nghKinjjji.jpg","release_date":"2018-11-20","title":"Ralph Breaks the Internet","vote_average":7.3,"vote_count":1416}])
            response = app.suggestion()
            print('response :: ', response)
            self.assertEqual(response, 200)
        # assert_is_not_none(response)

if __name__ == "__main__":
    # test_suggestion = SuggestionTest()
    # test_suggestion.init_app(app)

    unittest.main()