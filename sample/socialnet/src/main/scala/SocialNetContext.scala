package cap.jeeves.socialnet

/*
 * User records for jconf case study.
 */

import SocialNetBackend._
import cap.scalasmt.Formula

case class SocialNetContext ( val viewer: User ) extends JeevesRecord
