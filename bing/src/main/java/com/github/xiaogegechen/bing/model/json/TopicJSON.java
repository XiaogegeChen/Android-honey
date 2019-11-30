package com.github.xiaogegechen.bing.model.json;

import com.github.xiaogegechen.common.network.Checkable;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopicJSON implements Checkable {
    /**
     * result : {"module_type":200,"topic_count":11,"topic_list":[{"topic_title":"江西鄱阳湖干旱持续","topic_cover_url":"https://tse1-mm.cn.bing.net/th?id=OET.17b206b5449d4737b46d3affb8f88aa5&w=272&h=272&c=7&rs=1&o=5&pid=1.9","topic_type":1000},{"topic_title":"吐谷浑王族墓葬现身甘肃","topic_cover_url":"https://tse1-mm.cn.bing.net/th?id=OET.486f4317c6cb4725a9ee02603c977d0f&w=272&h=135&c=7&rs=1&o=5&pid=1.9","topic_type":1001},{"topic_title":"雪龙2号完成中山站航道破冰","topic_cover_url":"https://tse1-mm.cn.bing.net/th?id=OET.fc8f95e96dd6400093e7357ffbd18ad2&w=272&h=135&c=7&rs=1&o=5&pid=1.9","topic_type":1002},{"topic_title":"青花主题婚纱秀","topic_cover_url":"https://tse1-mm.cn.bing.net/th?id=OET.4a2a70942e2e441ab5f7d396252831f1&w=272&h=135&c=7&rs=1&o=5&pid=1.9","topic_type":1003},{"topic_title":"冰城\u201c撸鸭\u201d模式悄然开启","topic_cover_url":"https://tse1-mm.cn.bing.net/th?id=OET.7f6338cfdd4b43659782708fd5a181e1&w=272&h=135&c=7&rs=1&o=5&pid=1.9","topic_type":1004},{"topic_title":"全国医保电子凭证","topic_cover_url":"https://tse1-mm.cn.bing.net/th?id=OET.8543132349dd4d559c52f2bde775607b&w=272&h=135&c=7&rs=1&o=5&pid=1.9","topic_type":1005},{"topic_title":"胡歌吴磊同框","topic_cover_url":"https://tse1-mm.cn.bing.net/th?id=OET.899ca7dc3e414a259dc954baaee46465&w=272&h=135&c=7&rs=1&o=5&pid=1.9","topic_type":1006},{"topic_title":"藏漂青年","topic_cover_url":"https://tse1-mm.cn.bing.net/th?id=OET.b8ee5a322d3b43c481a3bbd7a8cb6e6e&w=135&h=135&c=7&rs=1&o=5&pid=1.9","topic_type":1007},{"topic_title":"旅美大熊猫回家","topic_cover_url":"https://tse1-mm.cn.bing.net/th?id=OET.5c457291ec004bb3a0d9ea144116264c&w=135&h=135&c=7&rs=1&o=5&pid=1.9","topic_type":1008},{"topic_title":"亚马逊将在拼多多开店","topic_cover_url":"https://tse1-mm.cn.bing.net/th?id=OET.ac573463de784758ab6370bc21b56fca&w=135&h=135&c=7&rs=1&o=5&pid=1.9","topic_type":1009},{"topic_title":"漠河取冰现场","topic_cover_url":"https://tse1-mm.cn.bing.net/th?id=OET.06d2867497624641bab6895d32675244&w=135&h=135&c=7&rs=1&o=5&pid=1.9","topic_type":1010}]}
     * error_code : 0
     * error_message : success
     */

    private Result result;
    @SerializedName("error_code")
    private String errorCode;
    @SerializedName("error_message")
    private String errorMessage;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String errorCode() {
        return errorCode;
    }

    @Override
    public String errorMessage() {
        return errorMessage;
    }

    public static class Result {
        /**
         * module_type : 200
         * topic_count : 11
         * topic_list : [{"topic_title":"江西鄱阳湖干旱持续","topic_cover_url":"https://tse1-mm.cn.bing.net/th?id=OET.17b206b5449d4737b46d3affb8f88aa5&w=272&h=272&c=7&rs=1&o=5&pid=1.9","topic_type":1000},{"topic_title":"吐谷浑王族墓葬现身甘肃","topic_cover_url":"https://tse1-mm.cn.bing.net/th?id=OET.486f4317c6cb4725a9ee02603c977d0f&w=272&h=135&c=7&rs=1&o=5&pid=1.9","topic_type":1001},{"topic_title":"雪龙2号完成中山站航道破冰","topic_cover_url":"https://tse1-mm.cn.bing.net/th?id=OET.fc8f95e96dd6400093e7357ffbd18ad2&w=272&h=135&c=7&rs=1&o=5&pid=1.9","topic_type":1002},{"topic_title":"青花主题婚纱秀","topic_cover_url":"https://tse1-mm.cn.bing.net/th?id=OET.4a2a70942e2e441ab5f7d396252831f1&w=272&h=135&c=7&rs=1&o=5&pid=1.9","topic_type":1003},{"topic_title":"冰城\u201c撸鸭\u201d模式悄然开启","topic_cover_url":"https://tse1-mm.cn.bing.net/th?id=OET.7f6338cfdd4b43659782708fd5a181e1&w=272&h=135&c=7&rs=1&o=5&pid=1.9","topic_type":1004},{"topic_title":"全国医保电子凭证","topic_cover_url":"https://tse1-mm.cn.bing.net/th?id=OET.8543132349dd4d559c52f2bde775607b&w=272&h=135&c=7&rs=1&o=5&pid=1.9","topic_type":1005},{"topic_title":"胡歌吴磊同框","topic_cover_url":"https://tse1-mm.cn.bing.net/th?id=OET.899ca7dc3e414a259dc954baaee46465&w=272&h=135&c=7&rs=1&o=5&pid=1.9","topic_type":1006},{"topic_title":"藏漂青年","topic_cover_url":"https://tse1-mm.cn.bing.net/th?id=OET.b8ee5a322d3b43c481a3bbd7a8cb6e6e&w=135&h=135&c=7&rs=1&o=5&pid=1.9","topic_type":1007},{"topic_title":"旅美大熊猫回家","topic_cover_url":"https://tse1-mm.cn.bing.net/th?id=OET.5c457291ec004bb3a0d9ea144116264c&w=135&h=135&c=7&rs=1&o=5&pid=1.9","topic_type":1008},{"topic_title":"亚马逊将在拼多多开店","topic_cover_url":"https://tse1-mm.cn.bing.net/th?id=OET.ac573463de784758ab6370bc21b56fca&w=135&h=135&c=7&rs=1&o=5&pid=1.9","topic_type":1009},{"topic_title":"漠河取冰现场","topic_cover_url":"https://tse1-mm.cn.bing.net/th?id=OET.06d2867497624641bab6895d32675244&w=135&h=135&c=7&rs=1&o=5&pid=1.9","topic_type":1010}]
         */

        @SerializedName("module_type")
        private String moduleType;
        @SerializedName("topic_count")
        private String topicCount;
        @SerializedName("topic_list")
        private List<Topic> topicList;

        public String getModuleType() {
            return moduleType;
        }

        public void setModuleType(String moduleType) {
            this.moduleType = moduleType;
        }

        public String getTopicCount() {
            return topicCount;
        }

        public void setTopicCount(String topicCount) {
            this.topicCount = topicCount;
        }

        public List<Topic> getTopicList() {
            return topicList;
        }

        public void setTopicList(List<Topic> topicList) {
            this.topicList = topicList;
        }

        public static class Topic {
            /**
             * topic_title : 江西鄱阳湖干旱持续
             * topic_cover_url : https://tse1-mm.cn.bing.net/th?id=OET.17b206b5449d4737b46d3affb8f88aa5&w=272&h=272&c=7&rs=1&o=5&pid=1.9
             * topic_type : 1000
             */

            @SerializedName("topic_title")
            private String topicTitle;
            @SerializedName("topic_cover_url")
            private String topicCoverUrl;
            @SerializedName("topic_type")
            private String topicType;

            public String getTopicTitle() {
                return topicTitle;
            }

            public void setTopicTitle(String topicTitle) {
                this.topicTitle = topicTitle;
            }

            public String getTopicCoverUrl() {
                return topicCoverUrl;
            }

            public void setTopicCoverUrl(String topicCoverUrl) {
                this.topicCoverUrl = topicCoverUrl;
            }

            public String getTopicType() {
                return topicType;
            }

            public void setTopicType(String topicType) {
                this.topicType = topicType;
            }
        }
    }
}
