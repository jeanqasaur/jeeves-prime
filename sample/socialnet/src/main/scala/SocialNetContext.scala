package cap.jeeves.pat

/*
 * User records for jconf case study.
 */

import SocialNetBackend._

case class SocialNetContext ( val viewer: User ) extends JeevesRecord
