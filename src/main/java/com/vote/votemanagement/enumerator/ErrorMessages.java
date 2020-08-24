package com.vote.votemanagement.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {

    ASSOCIATE_ALREADY_EXIST("Already exist an associate with this CPF"),
    ASSOCIATE_ALREADY_VOTED("This associate already voted in this assembly"),
    ASSOCIATE_NOT_FOUND("None associate was found with the CPF informed"),
    INVALID_CPF("This associate is unable to vote in any assembly"),

    INVALID_VOTE("Invalid vote value, the vote values allowed are: {SIM} and {NÃO}"),

    ASSEMBLY_NAME_ALREADY_EXIST("Already exist assembly with name informed, change the name and try again"),
    ASSEMBLY_NOT_FOUND("None assembly was found with the name informed"),

    ASSEMBLY_VOTING_SESSION_NOT_EXIST("There not voting session open for the assembly informed"),
    ASSEMBLY_VOTING_SESSION_NOT_OPEN("This voting session was finished for the assembly informed"),

    UNEXPECTED_ERROR("Unexpected error happened, please call application support for more details");

    private String message;

}
