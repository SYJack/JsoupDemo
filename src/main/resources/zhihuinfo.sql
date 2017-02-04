CREATE TABLE `zhihuuserinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(11) NOT NULL,
  `gender` int(3) NOT NULL,
  `business` varchar(11) NOT NULL,
  `company` varchar(11) NOT NULL,
  `position` varchar(11) NOT NULL,
  `education` varchar(11) NOT NULL,
  `major` varchar(11) NOT NULL,
  `answersNum` varchar(11) NOT NULL,
  `starsNum` varchar(11) NOT NULL,
  `thxNum` varchar(11) NOT NULL,
  `followingNum` varchar(11) NOT NULL,
  `followersNum` varchar(11) NOT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
