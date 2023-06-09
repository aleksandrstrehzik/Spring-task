package ru.clevertec.ecl.service.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.ecl.repository.entity.Tag;

@AllArgsConstructor
@NoArgsConstructor(staticName = "aTag")
@With
@Getter
public class TagTestBuilder  implements TestBuilder<Tag>{

    private Long id = null;
    private String name = "test name";

    @Override
    public Tag build() {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);
        return tag;
    }
}
