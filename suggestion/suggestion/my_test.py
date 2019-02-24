import mock
import pytest
import app
import json
from pytest_mock import mocker
import requests, requests_mock

@requests_mock.Mocker()
def test(m):
    test_app = app.app
    test_client = test_app.test_client()
    test_app.testing = True
    m.get('http://test.com', json=[{'a': 'b'}] )
    init = test_client.get('/test')
    print(init.data)
    # assert b'"data" == init.data
    # assert init.status_code == 200

test()