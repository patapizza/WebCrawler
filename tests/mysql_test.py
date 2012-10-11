#!/usr/bin/python2.7

# using Python 2.x for MySQLdb
# in the near future, may wish moving to petehunt/PyMySQL

import MySQLdb
import unittest

class MySQLTest(unittest.TestCase):

    def setUp(self):
        self.cx = None

    def test_connection(self):
        try:
            cx = MySQLdb.connect("localhost", "crawler", "student", "webcrawler")
            cx.query("SELECT value FROM words WHERE id=97891")
            res = cx.use_result()
            self.assertEquals("sleepy_dick", "%s" % res.fetch_row()[0])
        except MySQLdb.Error as e:
            print("Error %d: %s" % (e.args[0], e.args[1]))

    def tearDown(self):
        if self.cx:
            self.cx.close()

if __name__ == '__main__':
    unittest.main()
